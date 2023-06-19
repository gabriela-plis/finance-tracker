import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { SidebarState } from "../types/sidebarTypes";

const initialState = {
    active: 'home'
} as SidebarState;

const sidebarSlice = createSlice({
    name: 'sidebar',
    initialState,
    reducers: {
        setActive: (state, action: PayloadAction<string>) => {
            state.active = action.payload;
        }
    }
})

export const {
    setActive
} = sidebarSlice.actions;

export default sidebarSlice.reducer;