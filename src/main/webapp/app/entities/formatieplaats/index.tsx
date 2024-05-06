import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formatieplaats from './formatieplaats';
import FormatieplaatsDetail from './formatieplaats-detail';
import FormatieplaatsUpdate from './formatieplaats-update';
import FormatieplaatsDeleteDialog from './formatieplaats-delete-dialog';

const FormatieplaatsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formatieplaats />} />
    <Route path="new" element={<FormatieplaatsUpdate />} />
    <Route path=":id">
      <Route index element={<FormatieplaatsDetail />} />
      <Route path="edit" element={<FormatieplaatsUpdate />} />
      <Route path="delete" element={<FormatieplaatsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormatieplaatsRoutes;
