import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locatiekadastraleonroerendezaak from './locatiekadastraleonroerendezaak';
import LocatiekadastraleonroerendezaakDetail from './locatiekadastraleonroerendezaak-detail';
import LocatiekadastraleonroerendezaakUpdate from './locatiekadastraleonroerendezaak-update';
import LocatiekadastraleonroerendezaakDeleteDialog from './locatiekadastraleonroerendezaak-delete-dialog';

const LocatiekadastraleonroerendezaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locatiekadastraleonroerendezaak />} />
    <Route path="new" element={<LocatiekadastraleonroerendezaakUpdate />} />
    <Route path=":id">
      <Route index element={<LocatiekadastraleonroerendezaakDetail />} />
      <Route path="edit" element={<LocatiekadastraleonroerendezaakUpdate />} />
      <Route path="delete" element={<LocatiekadastraleonroerendezaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocatiekadastraleonroerendezaakRoutes;
