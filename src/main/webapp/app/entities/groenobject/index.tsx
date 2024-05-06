import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Groenobject from './groenobject';
import GroenobjectDetail from './groenobject-detail';
import GroenobjectUpdate from './groenobject-update';
import GroenobjectDeleteDialog from './groenobject-delete-dialog';

const GroenobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Groenobject />} />
    <Route path="new" element={<GroenobjectUpdate />} />
    <Route path=":id">
      <Route index element={<GroenobjectDetail />} />
      <Route path="edit" element={<GroenobjectUpdate />} />
      <Route path="delete" element={<GroenobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GroenobjectRoutes;
