import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Areaal from './areaal';
import AreaalDetail from './areaal-detail';
import AreaalUpdate from './areaal-update';
import AreaalDeleteDialog from './areaal-delete-dialog';

const AreaalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Areaal />} />
    <Route path="new" element={<AreaalUpdate />} />
    <Route path=":id">
      <Route index element={<AreaalDetail />} />
      <Route path="edit" element={<AreaalUpdate />} />
      <Route path="delete" element={<AreaalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AreaalRoutes;
