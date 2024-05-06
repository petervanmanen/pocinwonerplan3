import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ziekmeldingleerlingenvervoer from './ziekmeldingleerlingenvervoer';
import ZiekmeldingleerlingenvervoerDetail from './ziekmeldingleerlingenvervoer-detail';
import ZiekmeldingleerlingenvervoerUpdate from './ziekmeldingleerlingenvervoer-update';
import ZiekmeldingleerlingenvervoerDeleteDialog from './ziekmeldingleerlingenvervoer-delete-dialog';

const ZiekmeldingleerlingenvervoerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ziekmeldingleerlingenvervoer />} />
    <Route path="new" element={<ZiekmeldingleerlingenvervoerUpdate />} />
    <Route path=":id">
      <Route index element={<ZiekmeldingleerlingenvervoerDetail />} />
      <Route path="edit" element={<ZiekmeldingleerlingenvervoerUpdate />} />
      <Route path="delete" element={<ZiekmeldingleerlingenvervoerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZiekmeldingleerlingenvervoerRoutes;
