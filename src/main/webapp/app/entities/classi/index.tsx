import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classi from './classi';
import ClassiDetail from './classi-detail';
import ClassiUpdate from './classi-update';
import ClassiDeleteDialog from './classi-delete-dialog';

const ClassiRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classi />} />
    <Route path="new" element={<ClassiUpdate />} />
    <Route path=":id">
      <Route index element={<ClassiDetail />} />
      <Route path="edit" element={<ClassiUpdate />} />
      <Route path="delete" element={<ClassiDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassiRoutes;
