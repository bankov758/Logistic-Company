"use server";

import axios from "@/lib/axios";
import {FormState} from "@/lib/actions";
import {selectorItem} from "@/components/UI/DataSelectorWrapper";
import {getCookies} from "@/lib/auth";
import {AxiosError} from "axios";

// COMPANY ACTIONS START

export const createCompany = async (
    initialState: FormState,
    formData: FormData,
) => {
    const newCompanyName = formData.get("company_name") as string;

    try {

        if (!newCompanyName.trim()) {
            return {
                message: '',
                errors: "Company name cannot be empty!"
            }
        }

        const requestData = {
            name: newCompanyName.trim(),
        };

        const jsession = await getCookies();

        await axios.post('/companies', requestData, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "You've successfully create a company!",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: (error as Error).message
        };
    }
};

export const deleteCompany = async (selectedCompany: selectorItem) => {

    try {
        const jsession = await getCookies();

        await axios.delete(`/companies/${selectedCompany.id}`, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });
        
        return {
            message: "Company was successfully deleted!",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to delete the company!'
        };
    }
}

export const editCompany = async (updatedCompanyName: string, selectedCompany: selectorItem) => {

    try {
        const requestedData = {
            id: selectedCompany.id,
            name: updatedCompanyName
        }

        const jsession = await getCookies();

        await axios.put(`/companies`, requestedData, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "Company was successfully updated! ",
            errors: ""
        }
    } catch (error) {
        return {
            message: '',
            errors: 'Failed to update the company'
        };
    }
}

export const addTariff = async (companyId: number, pricePerKg: number, officeDiscount: number) => {

    try {
        const requestedData = {
            pricePerKg: pricePerKg,
            officeDiscount,
            companyId: companyId
        }

        const jsession = await getCookies();

        await axios.post(`/tariffs`, requestedData, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "Tariff was successfully added!",
            errors: ""
        }
    } catch (error) {
        return {
            message: '',
            errors: 'Failed to add a tariff to the company!'
        };
    }
}


// COMPANY ACTIONS END

// USER ACTIONS START

export const deleteUser = async (initialState: FormState, userId: number)=> {
    try {
        const jsession = await getCookies();

        await axios.delete(`/users/${userId}`, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "User was successfully deleted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to deleted the chosen user!'
        };
    }
}

export const promoteUserIntoEmployee = async (userId: number, officeId: number, initialState: FormState) => {
    try {
        const jsession = await getCookies();

        await axios.put(`/users/${userId}/make-office-employee/${officeId}`, {},{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            },
        });

        return {
            message: "User was successfully promoted into employee! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to promote the chosen user into employee!'
        };
    }
}

export const promoteUserIntoCourier = async (userId: number, companyId: number, initialState: FormState) => {
    try {
        const jsession = await getCookies();

        console.log(userId);

        if(userId) {
            console.log("promote user: : " );
            console.log(userId);
            console.log(companyId);
            console.log("show error: ")


            await axios.put(`/users/${userId}/make-courier/${companyId}`,{},{
                headers: {
                    Cookie: `JSESSIONID=${jsession?.value}`
                },
            });

            return {
                message: "User was successfully promoted into courier! ",
                errors: ""
            }
        } else {
            return {
                message: "",
                errors: "Please wait a second",
            }
        }
    } catch (error) {
        // Handle non-Axios errors
        console.log(error);
        if (error instanceof Error) {
            return {
                message: "",
                errors: error.message || "Something went wrong!",
            };
        }
        // Check if it's an Axios error with a modified structure
        if( error && typeof error === 'object' && "status" in error && "message" in error && typeof error.message === "string" ) {
            return {
                message: "",
                errors: error.message
            }
        }
        // Handle any other potential errors
        return {
            message: "",
            errors: "Something went wrong",
        };
    }
}

// USER ACTIONS END

// EMPLOYEE ACTIONS START

export const deleteEmployee = async (initialState: FormState, employeeId: number)=> {
    try {
        const jsession = await getCookies();

        await axios.delete(`/office-employees/${employeeId}`, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Employee was successfully deleted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to deleted the chosen employee!'
        };
    }
}

export const demoteEmployee = async (initialState: FormState, userId: number) => {

    const requestedData = {
        userId,
        role: "USER",
    }

    try {
        const jsession = await getCookies();

        await axios.put(`/office-employees/demote/${userId}`, requestedData,{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Employee was successfully demoted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to demote the chosen employee!'
        };
    }
}

export const makeCourier = async (initialState: FormState, userId: number) => {

    try {
        const requestData = {}

        const jsession = await getCookies();

        await axios.put(`/office-employees/make-courier/${userId}`, requestData,{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Employee was successfully appointed into courier! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to make the chosen employee into courier!'
        };
    }
}

// EMPLOYEE ACTIONS END

// OFFICE ACTIONS START

export const getOffices = async (companyId: number): Promise<selectorItem[] | null> => {

    try {
        const jsession = await getCookies();

        const response = await axios.get(`/companies/${companyId}/offices`, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return response.data.map((office: any) => ({
            title: office.address,
            code: office.address.split(' ').join('_'),
            id: office.id,
        }));

    } catch (err) {
        if (err instanceof AxiosError) {
            throw err;
        }
    }

    return null;

}

export const addOffice = async (companyId: number, officeLocation: string) => {

    try {
        const requestedData = {
            address: officeLocation,
            companyId
        }

        const jsession = await getCookies();

        await axios.post(`/offices`, requestedData, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "New office was successfully added! ",
            errors: ""
        }
    } catch (error) {
        return {
            message: '',
            errors: 'Failed to add a new office to the company!'
        };
    }
}

export const editOffice = async (officeId: number, companyId: {  id: number; name: string; }, initialState: FormState, formData: FormData) => {

    try {
        const requestedData = {
            id: officeId,
            address: formData.get('address'),
            companyId
        }

        const jsession = await getCookies();

        await axios.put(`/offices`, requestedData, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "New office was successfully edited! ",
            errors: ""
        }
    } catch (error) {
        console.log(error);
        return {
            message: '',
            errors: 'Failed to edit the office!'
        };
    }
}

export const deleteOffice = async (initialState: FormState, officeId: number)=> {
    try {
        const jsession = await getCookies();

        await axios.delete(`/offices/${officeId}`,{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        });

        return {
            message: "Office was successfully deleted! ",
            errors: ""
        }
    } catch (error) {
        return {
            message: '',
            errors: 'Failed to delete the chosen office!'
        };
    }
}

// OFFICE ACTIONS END

// COURIER ACTIONS START

export const deleteCourier = async (initialState: FormState, courierId: number)=> {
    try {
        const jsession = await getCookies();

        await axios.delete(`/couriers/${courierId}`, {
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Courier was successfully deleted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to deleted the chosen courier!'
        };
    }
}

export const demoteCourier = async (initialState: FormState, userId: number) => {

    const requestedData = {
        userId,
        role: "USER",
    }

    try {
        const jsession = await getCookies();
        await axios.put(`/couriers/demote/${userId}`, requestedData,{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Courier was successfully demoted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to demote the chosen courier!'
        };
    }
}

export const promoteCourierIntoEmployee = async ( userId: number, officeId: number, initialState: FormState) => {

    try {
        const jsession = await getCookies();
        await axios.put(`/couriers/${userId}/make-office-employee/${officeId}`, {},{
            headers: {
                Cookie: `JSESSIONID=${jsession?.value}`
            }
        })

        return {
            message: "Courier was successfully promoted! ",
            errors: ""
        }

    } catch (error) {
        return {
            message: '',
            errors: 'Failed to promote the chosen courier!'
        };
    }
}

// COURIER ACTIONS END