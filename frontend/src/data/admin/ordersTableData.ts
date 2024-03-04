import { category, column } from "@/components/home/Table";

//office columns
export const officeColumns: column[] = [
    {
        title: "Address",
        code: "address",
        id: 1,
    },
    {
        title: "Company Name",
        code: "company_name",
        id: 2,
    },
    {
        title: "Actions",
        code: "adminInterfaceActions",
        hide: true,
        id: 3,
    },
]

export const officeCategories: category[] = [
    {
        title: 'Offices',
        code: 'offices',
        id: 1
    }
];

//client + employee columns
export const tableColumns: column[] = [
    {
        title: "First name",
        code: "first_name",
        id: 1,
    },
    {
        title: "Last name",
        code: "last_name",
        id: 2,
    },
    {
        title: "Actions",
        code: "actions",
        hide: true,
        id: 6,
    },
    
];

//client + employee category
export const categories: category[] = [
    {
        title: "Clients",
        code: "clients",
        id: 1,
    },
    {
        title: "Employees",
        code: "employees",
        id: 2,
    },
];