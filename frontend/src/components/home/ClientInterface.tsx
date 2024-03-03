import {categories, data, tableColumns} from "@/data/client/ordersTableData";
import Table from "./EmployeeInterface/Table";

const ClientInterface = () => {
    return (
        <>
            <h3 className="flex justify-start w-full">Welcome user! This is your home page.</h3>
            <br></br>
            <Table
                columns={tableColumns}
                categories={categories}
                data={data}
            />
        </>
    );
}

export default ClientInterface;