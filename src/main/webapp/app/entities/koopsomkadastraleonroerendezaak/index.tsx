import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Koopsomkadastraleonroerendezaak from './koopsomkadastraleonroerendezaak';
import KoopsomkadastraleonroerendezaakDetail from './koopsomkadastraleonroerendezaak-detail';
import KoopsomkadastraleonroerendezaakUpdate from './koopsomkadastraleonroerendezaak-update';
import KoopsomkadastraleonroerendezaakDeleteDialog from './koopsomkadastraleonroerendezaak-delete-dialog';

const KoopsomkadastraleonroerendezaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Koopsomkadastraleonroerendezaak />} />
    <Route path="new" element={<KoopsomkadastraleonroerendezaakUpdate />} />
    <Route path=":id">
      <Route index element={<KoopsomkadastraleonroerendezaakDetail />} />
      <Route path="edit" element={<KoopsomkadastraleonroerendezaakUpdate />} />
      <Route path="delete" element={<KoopsomkadastraleonroerendezaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KoopsomkadastraleonroerendezaakRoutes;
