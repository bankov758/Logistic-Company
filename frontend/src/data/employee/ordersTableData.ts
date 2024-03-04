import { category, column } from "@/components/home/Table";

export const tableColumns: column[] = [
    {
        title: "Departure place",
        code: "departureAddress",
        id: 1,
    },
    {
        title: "Arrival place",
        code: "arrivalAddress",
        id: 2,
    },
    {
        title: "Sender",
        code: "sender",
        id: 3,
    },
    {
        title: "Receiver",
        code: "receiver",
        id: 4,
    },
    {
        title: "Weight",
        code: "weight",
        id: 5,
    },
    {
        title: "Date",
        code: "sentDate",
        id: 6,
    }, 
    {
        title: "Status",
        code: "status",
        id: 7,
    },
    {
        title: "Price",
        code: "price",
        id: 8,
    },
    {
        title: "Employee",
        code: "employee",
        id: 9,
    },    
    {
        title: "Actions",
        code: "employeeInterfaceActions",
        hide: true,
        id: 10,
    },
];

export const categories: category[] = [
    {
        title: "Registered",
        code: "registered",
        id: 1,
    },
];