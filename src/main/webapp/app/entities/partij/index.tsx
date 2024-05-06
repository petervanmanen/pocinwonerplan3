import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Partij from './partij';
import PartijDetail from './partij-detail';
import PartijUpdate from './partij-update';
import PartijDeleteDialog from './partij-delete-dialog';

const PartijRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Partij />} />
    <Route path="new" element={<PartijUpdate />} />
    <Route path=":id">
      <Route index element={<PartijDetail />} />
      <Route path="edit" element={<PartijUpdate />} />
      <Route path="delete" element={<PartijDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PartijRoutes;
