import {categories, tableColumns} from "@/data/client/ordersTableData";
import Table, {item} from "./Table";
import {getSession} from "@/lib/auth";
import {useEffect, useState} from "react";

const ClientInterface = () => {
    const [session, setSession] = useState<null | {username: string; roles: string[]}>();
    const [data, setData] = useState<item[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        getSession()
            .then((response) => {
                setSession(response)

                fetch("http://localhost:8080/api/shipments", {
                    headers: {
                        "Authorization": response?.username,
                        "Content-Type": "application/json",
                        Accept: "*/*"
                    }
                })
                .then(response => response.json())
                .then(data => setData(data))
                .catch(error => setError(error));
            })
    }, []);

    return (
        <>
            <h3 className="flex justify-start w-full">Welcome {session?.username || "Anonymous"}! This is your home page.</h3>
            <br></br>
            { data &&
                <Table
                    columns={tableColumns}
                    categories={categories}
                    data={data.map((item) => {
                        if( session?.username === item?.sender ) {
                           return {
                               ...item,//spread operator
                               category: "sent"
                           }
                        } else if( session?.username === item?.receiver ) {
                            return {
                                ...item,//spread operator
                                category: "received"
                            }
                        }
                        //TODO: modify
                        return {
                            ...item,//spread operator
                            category: "sent"
                        };
                    })}
                />
            }
        </>
    );
}

export default ClientInterface;