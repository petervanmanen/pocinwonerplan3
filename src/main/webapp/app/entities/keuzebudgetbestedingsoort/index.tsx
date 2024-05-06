import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Keuzebudgetbestedingsoort from './keuzebudgetbestedingsoort';
import KeuzebudgetbestedingsoortDetail from './keuzebudgetbestedingsoort-detail';
import KeuzebudgetbestedingsoortUpdate from './keuzebudgetbestedingsoort-update';
import KeuzebudgetbestedingsoortDeleteDialog from './keuzebudgetbestedingsoort-delete-dialog';

const KeuzebudgetbestedingsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Keuzebudgetbestedingsoort />} />
    <Route path="new" element={<KeuzebudgetbestedingsoortUpdate />} />
    <Route path=":id">
      <Route index element={<KeuzebudgetbestedingsoortDetail />} />
      <Route path="edit" element={<KeuzebudgetbestedingsoortUpdate />} />
      <Route path="delete" element={<KeuzebudgetbestedingsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KeuzebudgetbestedingsoortRoutes;
