import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bankrekening from './bankrekening';
import BankrekeningDetail from './bankrekening-detail';
import BankrekeningUpdate from './bankrekening-update';
import BankrekeningDeleteDialog from './bankrekening-delete-dialog';

const BankrekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bankrekening />} />
    <Route path="new" element={<BankrekeningUpdate />} />
    <Route path=":id">
      <Route index element={<BankrekeningDetail />} />
      <Route path="edit" element={<BankrekeningUpdate />} />
      <Route path="delete" element={<BankrekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankrekeningRoutes;
