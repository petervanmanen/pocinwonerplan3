import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kaart from './kaart';
import KaartDetail from './kaart-detail';
import KaartUpdate from './kaart-update';
import KaartDeleteDialog from './kaart-delete-dialog';

const KaartRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kaart />} />
    <Route path="new" element={<KaartUpdate />} />
    <Route path=":id">
      <Route index element={<KaartDetail />} />
      <Route path="edit" element={<KaartUpdate />} />
      <Route path="delete" element={<KaartDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KaartRoutes;
