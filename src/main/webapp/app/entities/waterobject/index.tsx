import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Waterobject from './waterobject';
import WaterobjectDetail from './waterobject-detail';
import WaterobjectUpdate from './waterobject-update';
import WaterobjectDeleteDialog from './waterobject-delete-dialog';

const WaterobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Waterobject />} />
    <Route path="new" element={<WaterobjectUpdate />} />
    <Route path=":id">
      <Route index element={<WaterobjectDetail />} />
      <Route path="edit" element={<WaterobjectUpdate />} />
      <Route path="delete" element={<WaterobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WaterobjectRoutes;
