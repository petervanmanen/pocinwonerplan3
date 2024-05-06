import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gezinsmigrantenoverigemigrant from './gezinsmigrantenoverigemigrant';
import GezinsmigrantenoverigemigrantDetail from './gezinsmigrantenoverigemigrant-detail';
import GezinsmigrantenoverigemigrantUpdate from './gezinsmigrantenoverigemigrant-update';
import GezinsmigrantenoverigemigrantDeleteDialog from './gezinsmigrantenoverigemigrant-delete-dialog';

const GezinsmigrantenoverigemigrantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gezinsmigrantenoverigemigrant />} />
    <Route path="new" element={<GezinsmigrantenoverigemigrantUpdate />} />
    <Route path=":id">
      <Route index element={<GezinsmigrantenoverigemigrantDetail />} />
      <Route path="edit" element={<GezinsmigrantenoverigemigrantUpdate />} />
      <Route path="delete" element={<GezinsmigrantenoverigemigrantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GezinsmigrantenoverigemigrantRoutes;
