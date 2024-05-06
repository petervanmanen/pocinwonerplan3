import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bergingsbassin from './bergingsbassin';
import BergingsbassinDetail from './bergingsbassin-detail';
import BergingsbassinUpdate from './bergingsbassin-update';
import BergingsbassinDeleteDialog from './bergingsbassin-delete-dialog';

const BergingsbassinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bergingsbassin />} />
    <Route path="new" element={<BergingsbassinUpdate />} />
    <Route path=":id">
      <Route index element={<BergingsbassinDetail />} />
      <Route path="edit" element={<BergingsbassinUpdate />} />
      <Route path="delete" element={<BergingsbassinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BergingsbassinRoutes;
