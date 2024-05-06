import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Taakveld from './taakveld';
import TaakveldDetail from './taakveld-detail';
import TaakveldUpdate from './taakveld-update';
import TaakveldDeleteDialog from './taakveld-delete-dialog';

const TaakveldRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Taakveld />} />
    <Route path="new" element={<TaakveldUpdate />} />
    <Route path=":id">
      <Route index element={<TaakveldDetail />} />
      <Route path="edit" element={<TaakveldUpdate />} />
      <Route path="delete" element={<TaakveldDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TaakveldRoutes;
