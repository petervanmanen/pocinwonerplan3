import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kademuur from './kademuur';
import KademuurDetail from './kademuur-detail';
import KademuurUpdate from './kademuur-update';
import KademuurDeleteDialog from './kademuur-delete-dialog';

const KademuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kademuur />} />
    <Route path="new" element={<KademuurUpdate />} />
    <Route path=":id">
      <Route index element={<KademuurDetail />} />
      <Route path="edit" element={<KademuurUpdate />} />
      <Route path="delete" element={<KademuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KademuurRoutes;
