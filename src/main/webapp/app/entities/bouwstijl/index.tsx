import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwstijl from './bouwstijl';
import BouwstijlDetail from './bouwstijl-detail';
import BouwstijlUpdate from './bouwstijl-update';
import BouwstijlDeleteDialog from './bouwstijl-delete-dialog';

const BouwstijlRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwstijl />} />
    <Route path="new" element={<BouwstijlUpdate />} />
    <Route path=":id">
      <Route index element={<BouwstijlDetail />} />
      <Route path="edit" element={<BouwstijlUpdate />} />
      <Route path="delete" element={<BouwstijlDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwstijlRoutes;
