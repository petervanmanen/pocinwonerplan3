import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Flyover from './flyover';
import FlyoverDetail from './flyover-detail';
import FlyoverUpdate from './flyover-update';
import FlyoverDeleteDialog from './flyover-delete-dialog';

const FlyoverRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Flyover />} />
    <Route path="new" element={<FlyoverUpdate />} />
    <Route path=":id">
      <Route index element={<FlyoverDetail />} />
      <Route path="edit" element={<FlyoverUpdate />} />
      <Route path="delete" element={<FlyoverDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FlyoverRoutes;
