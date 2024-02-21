import { category, column, item } from "@/components/home/EmployeeInterface/Table";

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
        code: "actions",
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
        id: 1,
        category: 'offices'
    },
];

//client + employee columns
export const tableColumns: column[] = [
    {
        title: "Departure place",
        code: "departure",
        id: 1,
    },
    {
        title: "Arrival place",
        code: "arrival",
        id: 2,
    },
    {
        title: "Sender name",
        code: "sender",
        id: 3,
    },
    {
        title: "Receiver name",
        code: "receiver",
        id: 4,
    },
    {
        title: "Status",
        code: "status",
        id: 5,
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


export const data: item[] = [
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "clients",
        id: 1,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "clients",
        id: 2,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "clients",
        id: 3,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "employees",
        id: 4,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "employees",
        id: 5,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "employees",
        id: 6,
    },
];