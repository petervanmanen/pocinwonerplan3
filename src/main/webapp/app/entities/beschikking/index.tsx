import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beschikking from './beschikking';
import BeschikkingDetail from './beschikking-detail';
import BeschikkingUpdate from './beschikking-update';
import BeschikkingDeleteDialog from './beschikking-delete-dialog';

const BeschikkingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beschikking />} />
    <Route path="new" element={<BeschikkingUpdate />} />
    <Route path=":id">
      <Route index element={<BeschikkingDetail />} />
      <Route path="edit" element={<BeschikkingUpdate />} />
      <Route path="delete" element={<BeschikkingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeschikkingRoutes;
