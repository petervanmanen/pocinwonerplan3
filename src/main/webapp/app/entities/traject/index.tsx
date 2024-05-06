import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Traject from './traject';
import TrajectDetail from './traject-detail';
import TrajectUpdate from './traject-update';
import TrajectDeleteDialog from './traject-delete-dialog';

const TrajectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Traject />} />
    <Route path="new" element={<TrajectUpdate />} />
    <Route path=":id">
      <Route index element={<TrajectDetail />} />
      <Route path="edit" element={<TrajectUpdate />} />
      <Route path="delete" element={<TrajectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrajectRoutes;
