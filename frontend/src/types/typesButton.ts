import { ButtonHTMLAttributes } from "react";

export interface IButton {
    loading: boolean;
    type: ButtonHTMLAttributes<HTMLButtonElement>["type"];
    buttonFunction: string;
}