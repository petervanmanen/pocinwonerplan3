import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bezetting from './bezetting';
import BezettingDetail from './bezetting-detail';
import BezettingUpdate from './bezetting-update';
import BezettingDeleteDialog from './bezetting-delete-dialog';

const BezettingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bezetting />} />
    <Route path="new" element={<BezettingUpdate />} />
    <Route path=":id">
      <Route index element={<BezettingDetail />} />
      <Route path="edit" element={<BezettingUpdate />} />
      <Route path="delete" element={<BezettingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BezettingRoutes;
