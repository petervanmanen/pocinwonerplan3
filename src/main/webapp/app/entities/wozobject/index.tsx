import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wozobject from './wozobject';
import WozobjectDetail from './wozobject-detail';
import WozobjectUpdate from './wozobject-update';
import WozobjectDeleteDialog from './wozobject-delete-dialog';

const WozobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wozobject />} />
    <Route path="new" element={<WozobjectUpdate />} />
    <Route path=":id">
      <Route index element={<WozobjectDetail />} />
      <Route path="edit" element={<WozobjectUpdate />} />
      <Route path="delete" element={<WozobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WozobjectRoutes;
