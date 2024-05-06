import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verlofaanvraag from './verlofaanvraag';
import VerlofaanvraagDetail from './verlofaanvraag-detail';
import VerlofaanvraagUpdate from './verlofaanvraag-update';
import VerlofaanvraagDeleteDialog from './verlofaanvraag-delete-dialog';

const VerlofaanvraagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verlofaanvraag />} />
    <Route path="new" element={<VerlofaanvraagUpdate />} />
    <Route path=":id">
      <Route index element={<VerlofaanvraagDetail />} />
      <Route path="edit" element={<VerlofaanvraagUpdate />} />
      <Route path="delete" element={<VerlofaanvraagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerlofaanvraagRoutes;
