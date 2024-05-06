import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Startformulieraanbesteden from './startformulieraanbesteden';
import StartformulieraanbestedenDetail from './startformulieraanbesteden-detail';
import StartformulieraanbestedenUpdate from './startformulieraanbesteden-update';
import StartformulieraanbestedenDeleteDialog from './startformulieraanbesteden-delete-dialog';

const StartformulieraanbestedenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Startformulieraanbesteden />} />
    <Route path="new" element={<StartformulieraanbestedenUpdate />} />
    <Route path=":id">
      <Route index element={<StartformulieraanbestedenDetail />} />
      <Route path="edit" element={<StartformulieraanbestedenUpdate />} />
      <Route path="delete" element={<StartformulieraanbestedenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StartformulieraanbestedenRoutes;
