# Logistic Company

# Table of Contents
- <a href="#about">About this project</a>
- <a href="#how-to-run">How to run the app on your computer</a>
- <a href="#features">Features</a>
- <a href="#project-structure">Project Structure</a>
- <a href="#tools">Tools</a>
- <a href="#application-pictures">Application Pictures</a>

# <p id="about">About this project</p>
Serves for the management of processes in a logistics company. The main activity of the company is to provide services receiving and delivering shipments.

# <p id="how-to-run">How to run the frontend part on your computer</p>

1. You can download the project ZIP file or you can clone the repository directly.
2. Open the project with IDE/Code Editor like VS Code or any Jetbrains product which supports the JavaScript syntax.
3. Install all modules that are listed on `package.json` file and their dependencies with `npm install` or `yarn install`.
4. Inside the `frontend` folder, create a new file named `.env.development` and paste the content from `.env.development.example`.
5. Type `npm run dev` to start the development server provided by NextJS. It will start on `http://localhost:3000`.


# <p id="features">Features</p>

- <strong>Client interface</strong>
    - Detailed information about the client's shipments.
    - Ability to delete its own account.

- <strong>Employee interface</strong>
    - Browse all shipments for a company in which the employee is a part of.
        - perform specific actions upon each shipment (e.g. delete, edit)
        - filter all shipments by providing a criteria (e.g. by employee name, by client name, the status of a shipment)
    - Ability to delete its own account
    - Create new shipments

- <strong>Admin interface</strong>
    - Search and select a specific company
        - view detailed information about the selected company
        - perform specific actions upon the selected company (e.g. delete, edit, add tariff, add office)
    - View company's employees table, offices table, clients table, couriers table
        - perform specific actions upon each table (e.g. delete, edit, promote/demote, etc.)

- <strong>Authentication</strong>
    - Login
        - log in with existing account
    - Register as a client
        - create new account
    - Choose an interface depending on the role.

# <p id="project-structure">Project Structure</p>
- Client
    - components
        - home
            - ClientInterface - client-related components
            - EmployeeInterface - employee-related components
            - AdminInterface - admin-related components
            - Table - reusable component across all interfaces that represents a table UI component
        - UI
            - UI-related components. The main purpose of these components is to reuse within the project.
    - data
        - All static data that is used to visualise different stuff
    - hooks
        - useInput - custom hooks that manages all the state for an individual input field
        - useHttp - encapsulate reusable logic for sending HTTP requests in a flexible and maintainable way
    - styles - stores css file that is used globally in the layout.tsx Root Layout
    - lib - Server Actions are asynchronous functions that are executed on the server. The main advantage is that they can be used both in Server and Client components to handle form submission.
- Server
    - <h1>TODO</h1>

# <p id="tools">Tools</p>

- <a href="https://react.dev/">React</a>
- <a href="https://nextjs.org/">Next.JS</a>
- <a href="https://https://tailwindcss.com/">TailwindCSS</a>

# <p id="application-pictures">Application Pictures</p>

## Desktop

## Mobile
