import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Geoobject from './geoobject';
import GeoobjectDetail from './geoobject-detail';
import GeoobjectUpdate from './geoobject-update';
import GeoobjectDeleteDialog from './geoobject-delete-dialog';

const GeoobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Geoobject />} />
    <Route path="new" element={<GeoobjectUpdate />} />
    <Route path=":id">
      <Route index element={<GeoobjectDetail />} />
      <Route path="edit" element={<GeoobjectUpdate />} />
      <Route path="delete" element={<GeoobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeoobjectRoutes;
