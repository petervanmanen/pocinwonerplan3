import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overigescheiding from './overigescheiding';
import OverigescheidingDetail from './overigescheiding-detail';
import OverigescheidingUpdate from './overigescheiding-update';
import OverigescheidingDeleteDialog from './overigescheiding-delete-dialog';

const OverigescheidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overigescheiding />} />
    <Route path="new" element={<OverigescheidingUpdate />} />
    <Route path=":id">
      <Route index element={<OverigescheidingDetail />} />
      <Route path="edit" element={<OverigescheidingUpdate />} />
      <Route path="delete" element={<OverigescheidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverigescheidingRoutes;
