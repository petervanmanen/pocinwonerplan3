import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kadastraleonroerendezaak from './kadastraleonroerendezaak';
import KadastraleonroerendezaakDetail from './kadastraleonroerendezaak-detail';
import KadastraleonroerendezaakUpdate from './kadastraleonroerendezaak-update';
import KadastraleonroerendezaakDeleteDialog from './kadastraleonroerendezaak-delete-dialog';

const KadastraleonroerendezaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kadastraleonroerendezaak />} />
    <Route path="new" element={<KadastraleonroerendezaakUpdate />} />
    <Route path=":id">
      <Route index element={<KadastraleonroerendezaakDetail />} />
      <Route path="edit" element={<KadastraleonroerendezaakUpdate />} />
      <Route path="delete" element={<KadastraleonroerendezaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KadastraleonroerendezaakRoutes;
