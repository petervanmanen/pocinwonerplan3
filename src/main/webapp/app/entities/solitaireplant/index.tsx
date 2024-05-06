import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Solitaireplant from './solitaireplant';
import SolitaireplantDetail from './solitaireplant-detail';
import SolitaireplantUpdate from './solitaireplant-update';
import SolitaireplantDeleteDialog from './solitaireplant-delete-dialog';

const SolitaireplantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Solitaireplant />} />
    <Route path="new" element={<SolitaireplantUpdate />} />
    <Route path=":id">
      <Route index element={<SolitaireplantDetail />} />
      <Route path="edit" element={<SolitaireplantUpdate />} />
      <Route path="delete" element={<SolitaireplantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SolitaireplantRoutes;
