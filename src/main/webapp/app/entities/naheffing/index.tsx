import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Naheffing from './naheffing';
import NaheffingDetail from './naheffing-detail';
import NaheffingUpdate from './naheffing-update';
import NaheffingDeleteDialog from './naheffing-delete-dialog';

const NaheffingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Naheffing />} />
    <Route path="new" element={<NaheffingUpdate />} />
    <Route path=":id">
      <Route index element={<NaheffingDetail />} />
      <Route path="edit" element={<NaheffingUpdate />} />
      <Route path="delete" element={<NaheffingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NaheffingRoutes;
