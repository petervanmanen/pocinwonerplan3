import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Resultaat from './resultaat';
import ResultaatDetail from './resultaat-detail';
import ResultaatUpdate from './resultaat-update';
import ResultaatDeleteDialog from './resultaat-delete-dialog';

const ResultaatRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Resultaat />} />
    <Route path="new" element={<ResultaatUpdate />} />
    <Route path=":id">
      <Route index element={<ResultaatDetail />} />
      <Route path="edit" element={<ResultaatUpdate />} />
      <Route path="delete" element={<ResultaatDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ResultaatRoutes;
