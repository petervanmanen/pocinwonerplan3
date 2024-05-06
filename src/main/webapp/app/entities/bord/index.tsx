import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bord from './bord';
import BordDetail from './bord-detail';
import BordUpdate from './bord-update';
import BordDeleteDialog from './bord-delete-dialog';

const BordRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bord />} />
    <Route path="new" element={<BordUpdate />} />
    <Route path=":id">
      <Route index element={<BordDetail />} />
      <Route path="edit" element={<BordUpdate />} />
      <Route path="delete" element={<BordDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BordRoutes;
