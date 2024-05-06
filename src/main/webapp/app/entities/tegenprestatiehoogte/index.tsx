import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tegenprestatiehoogte from './tegenprestatiehoogte';
import TegenprestatiehoogteDetail from './tegenprestatiehoogte-detail';
import TegenprestatiehoogteUpdate from './tegenprestatiehoogte-update';
import TegenprestatiehoogteDeleteDialog from './tegenprestatiehoogte-delete-dialog';

const TegenprestatiehoogteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tegenprestatiehoogte />} />
    <Route path="new" element={<TegenprestatiehoogteUpdate />} />
    <Route path=":id">
      <Route index element={<TegenprestatiehoogteDetail />} />
      <Route path="edit" element={<TegenprestatiehoogteUpdate />} />
      <Route path="delete" element={<TegenprestatiehoogteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TegenprestatiehoogteRoutes;
