import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nadaanvullingbrp from './nadaanvullingbrp';
import NadaanvullingbrpDetail from './nadaanvullingbrp-detail';
import NadaanvullingbrpUpdate from './nadaanvullingbrp-update';
import NadaanvullingbrpDeleteDialog from './nadaanvullingbrp-delete-dialog';

const NadaanvullingbrpRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nadaanvullingbrp />} />
    <Route path="new" element={<NadaanvullingbrpUpdate />} />
    <Route path=":id">
      <Route index element={<NadaanvullingbrpDetail />} />
      <Route path="edit" element={<NadaanvullingbrpUpdate />} />
      <Route path="delete" element={<NadaanvullingbrpDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NadaanvullingbrpRoutes;
