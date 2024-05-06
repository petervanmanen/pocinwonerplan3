import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeervlak from './parkeervlak';
import ParkeervlakDetail from './parkeervlak-detail';
import ParkeervlakUpdate from './parkeervlak-update';
import ParkeervlakDeleteDialog from './parkeervlak-delete-dialog';

const ParkeervlakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeervlak />} />
    <Route path="new" element={<ParkeervlakUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeervlakDetail />} />
      <Route path="edit" element={<ParkeervlakUpdate />} />
      <Route path="delete" element={<ParkeervlakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeervlakRoutes;
