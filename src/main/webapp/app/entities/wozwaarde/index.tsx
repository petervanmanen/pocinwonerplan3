import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wozwaarde from './wozwaarde';
import WozwaardeDetail from './wozwaarde-detail';
import WozwaardeUpdate from './wozwaarde-update';
import WozwaardeDeleteDialog from './wozwaarde-delete-dialog';

const WozwaardeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wozwaarde />} />
    <Route path="new" element={<WozwaardeUpdate />} />
    <Route path=":id">
      <Route index element={<WozwaardeDetail />} />
      <Route path="edit" element={<WozwaardeUpdate />} />
      <Route path="delete" element={<WozwaardeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WozwaardeRoutes;
