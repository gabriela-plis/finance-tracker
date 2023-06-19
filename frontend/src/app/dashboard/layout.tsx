import Sidebar from '@/components/Sidebar'
import { Providers } from '@/store/provider'
import { Inter } from 'next/font/google'


const inter = Inter({ subsets: ['latin'] })


export const metadata = {
    title: 'Zaprogramuj finanse',
    description: 'Zaprogramuj finanse',
}

export default function DashboardLayout({
    children,
} : {
    children: React.ReactNode
}) {
    return (
        <html lang="pl">
            <body className={inter.className}>
                <section className="flex min-h-screen min-w-screen">
                <Providers>
                    <Sidebar/>
                    {children}
                </Providers>              
                </section>
            </body>
        </html>
    )
}