import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortgrootte from './soortgrootte';
import SoortgrootteDetail from './soortgrootte-detail';
import SoortgrootteUpdate from './soortgrootte-update';
import SoortgrootteDeleteDialog from './soortgrootte-delete-dialog';

const SoortgrootteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortgrootte />} />
    <Route path="new" element={<SoortgrootteUpdate />} />
    <Route path=":id">
      <Route index element={<SoortgrootteDetail />} />
      <Route path="edit" element={<SoortgrootteUpdate />} />
      <Route path="delete" element={<SoortgrootteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortgrootteRoutes;
