import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Geweldsincident from './geweldsincident';
import GeweldsincidentDetail from './geweldsincident-detail';
import GeweldsincidentUpdate from './geweldsincident-update';
import GeweldsincidentDeleteDialog from './geweldsincident-delete-dialog';

const GeweldsincidentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Geweldsincident />} />
    <Route path="new" element={<GeweldsincidentUpdate />} />
    <Route path=":id">
      <Route index element={<GeweldsincidentDetail />} />
      <Route path="edit" element={<GeweldsincidentUpdate />} />
      <Route path="delete" element={<GeweldsincidentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeweldsincidentRoutes;
