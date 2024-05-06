import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vegetatieobject from './vegetatieobject';
import VegetatieobjectDetail from './vegetatieobject-detail';
import VegetatieobjectUpdate from './vegetatieobject-update';
import VegetatieobjectDeleteDialog from './vegetatieobject-delete-dialog';

const VegetatieobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vegetatieobject />} />
    <Route path="new" element={<VegetatieobjectUpdate />} />
    <Route path=":id">
      <Route index element={<VegetatieobjectDetail />} />
      <Route path="edit" element={<VegetatieobjectUpdate />} />
      <Route path="delete" element={<VegetatieobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VegetatieobjectRoutes;
