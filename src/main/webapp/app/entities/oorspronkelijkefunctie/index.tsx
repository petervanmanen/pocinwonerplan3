import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Oorspronkelijkefunctie from './oorspronkelijkefunctie';
import OorspronkelijkefunctieDetail from './oorspronkelijkefunctie-detail';
import OorspronkelijkefunctieUpdate from './oorspronkelijkefunctie-update';
import OorspronkelijkefunctieDeleteDialog from './oorspronkelijkefunctie-delete-dialog';

const OorspronkelijkefunctieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Oorspronkelijkefunctie />} />
    <Route path="new" element={<OorspronkelijkefunctieUpdate />} />
    <Route path=":id">
      <Route index element={<OorspronkelijkefunctieDetail />} />
      <Route path="edit" element={<OorspronkelijkefunctieUpdate />} />
      <Route path="delete" element={<OorspronkelijkefunctieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OorspronkelijkefunctieRoutes;
