import * as React from "react";
import { Admin, Resource, ListGuesser, defaultTheme } from "react-admin";
import { dataProvider } from "./dataProvider";
import { authProvider } from "./authProvider";
import Layout from "./Layout";
import contacts from "./contacts";
import companies from "./companies";
import deals from "./deals";
import { Dashboard } from "./dashboard/Dashboard";
import simpleRestProvider from "ra-data-json-server";
import { myAuth } from "./myAuth";

const data = simpleRestProvider("http://localhost:3080");
const App = () => (
  <Admin
    // dataProvider={dataProvider}
    // authProvider={authProvider}
    dataProvider={data}
    authProvider={myAuth}
    layout={Layout}
    dashboard={Dashboard}
    theme={{
      ...defaultTheme,
      palette: {
        background: {
          default: "#fafafb",
        },
      },
    }}
  >
    <Resource name="deals" {...deals} />
    <Resource name="contacts" {...contacts} />
    <Resource name="companies" {...companies} />
    <Resource name="contactNotes" />
    <Resource name="dealNotes" />
    <Resource name="tasks" list={ListGuesser} />
    <Resource name="sales" list={ListGuesser} />
    <Resource name="tags" list={ListGuesser} />
  </Admin>
);

export default App;
