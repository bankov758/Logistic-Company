import { category, column, item } from "@/components/home/Table";

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
        code: "actionOffice",
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

export const officeDataItems: item[] = [
    {
        address: "Sofia",
        company_name: "Speedy",
        id: 1,
        category: 'offices'
    },
    {
        address: "Varna",
        company_name: "Econt",
        id: 2,
        category: 'offices'
    },
    {
        address: "Stara Zagora",
        company_name: "Econt",
        id: 3,
        category: 'offices'
    },
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
        code: "actionTable",
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


export const data: item[] = [
    {
        first_name: "John",
        last_name: "Doe",
        category: "clients",
        id: 1,
    },
    {
        
        first_name: "John",
        last_name: "Doe",
        category: "clients",
        id: 2,
    },
    {
        
        first_name: "Jane",
        last_name: "Doe",
        category: "clients",
        id: 3,
    },
    {
       
        first_name: "John",
        last_name: "Doe",
        category: "employees",
        id: 4,
    },
    {
        
        first_name: "John",
        last_name: "Doe",
        category: "employees",
        id: 5,
    },
    {
       
        first_name: "Jane",
        last_name: "Doe",
        category: "employees",
        id: 6,
    },
];