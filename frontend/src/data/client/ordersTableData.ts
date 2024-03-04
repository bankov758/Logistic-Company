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
        title: "Date",
        code: "sentDate",
        id: 6,
    },
    {
        title: "Weight",
        code: "weight",
        id: 7,
    },
    {
        title: "Price",
        code: "price",
        id: 8,
    },
];

export const categories: category[] = [
    {
        title: "Sent",
        code: "sent",
        id: 1,
    },
    {
        title: "Received",
        code: "received",
        id: 2,
    },
];