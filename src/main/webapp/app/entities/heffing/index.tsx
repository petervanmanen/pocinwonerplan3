import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Heffing from './heffing';
import HeffingDetail from './heffing-detail';
import HeffingUpdate from './heffing-update';
import HeffingDeleteDialog from './heffing-delete-dialog';

const HeffingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Heffing />} />
    <Route path="new" element={<HeffingUpdate />} />
    <Route path=":id">
      <Route index element={<HeffingDetail />} />
      <Route path="edit" element={<HeffingUpdate />} />
      <Route path="delete" element={<HeffingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeffingRoutes;
