import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Historischerol from './historischerol';
import HistorischerolDetail from './historischerol-detail';
import HistorischerolUpdate from './historischerol-update';
import HistorischerolDeleteDialog from './historischerol-delete-dialog';

const HistorischerolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Historischerol />} />
    <Route path="new" element={<HistorischerolUpdate />} />
    <Route path=":id">
      <Route index element={<HistorischerolDetail />} />
      <Route path="edit" element={<HistorischerolUpdate />} />
      <Route path="delete" element={<HistorischerolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HistorischerolRoutes;
