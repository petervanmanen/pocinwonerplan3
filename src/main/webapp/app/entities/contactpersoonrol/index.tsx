import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Contactpersoonrol from './contactpersoonrol';
import ContactpersoonrolDetail from './contactpersoonrol-detail';
import ContactpersoonrolUpdate from './contactpersoonrol-update';
import ContactpersoonrolDeleteDialog from './contactpersoonrol-delete-dialog';

const ContactpersoonrolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Contactpersoonrol />} />
    <Route path="new" element={<ContactpersoonrolUpdate />} />
    <Route path=":id">
      <Route index element={<ContactpersoonrolDetail />} />
      <Route path="edit" element={<ContactpersoonrolUpdate />} />
      <Route path="delete" element={<ContactpersoonrolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContactpersoonrolRoutes;
