import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vlak from './vlak';
import VlakDetail from './vlak-detail';
import VlakUpdate from './vlak-update';
import VlakDeleteDialog from './vlak-delete-dialog';

const VlakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vlak />} />
    <Route path="new" element={<VlakUpdate />} />
    <Route path=":id">
      <Route index element={<VlakDetail />} />
      <Route path="edit" element={<VlakUpdate />} />
      <Route path="delete" element={<VlakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VlakRoutes;
