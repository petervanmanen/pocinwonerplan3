import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortwozobject from './soortwozobject';
import SoortwozobjectDetail from './soortwozobject-detail';
import SoortwozobjectUpdate from './soortwozobject-update';
import SoortwozobjectDeleteDialog from './soortwozobject-delete-dialog';

const SoortwozobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortwozobject />} />
    <Route path="new" element={<SoortwozobjectUpdate />} />
    <Route path=":id">
      <Route index element={<SoortwozobjectDetail />} />
      <Route path="edit" element={<SoortwozobjectUpdate />} />
      <Route path="delete" element={<SoortwozobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortwozobjectRoutes;
